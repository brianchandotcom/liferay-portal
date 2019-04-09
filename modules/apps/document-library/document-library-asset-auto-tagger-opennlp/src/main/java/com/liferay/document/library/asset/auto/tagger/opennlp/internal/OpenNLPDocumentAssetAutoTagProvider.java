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

package com.liferay.document.library.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.document.library.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.opennlp.internal.constants.OpenNLPDocumentAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class OpenNLPDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		try {
			return _getTagNames(fileEntry);
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptySet();
		}
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

	private OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration
			_getConfiguration(long companyId)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration.class,
			new CompanyServiceSettingsLocator(
				companyId,
				OpenNLPDocumentAssetAutoTagProviderConstants.SERVICE_NAME));
	}

	private String _getFileEntryContent(FileEntry fileEntry)
		throws IOException, PortalException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		try (InputStream inputStream = fileVersion.getContentStream(false)) {
			return FileUtil.extractText(inputStream, fileVersion.getFileName());
		}
	}

	private Collection<String> _getTagNames(FileEntry fileEntry)
		throws Exception {

		if (fileEntry.isRepositoryCapabilityProvided(
				TemporaryFileEntriesCapability.class) ||
			!_supportedContentTypes.contains(fileEntry.getMimeType())) {

			return Collections.emptyList();
		}

		OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration
			openNLPDocumentAssetAutoTagProviderCompanyConfiguration =
				_getConfiguration(fileEntry.getCompanyId());

		if (!openNLPDocumentAssetAutoTagProviderCompanyConfiguration.
				enabled()) {

			return Collections.emptyList();
		}

		SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(
			_sentenceModel);

		TokenizerME tokenizerME = new TokenizerME(_tokenizerModel);

		float confidenceThreshold =
			openNLPDocumentAssetAutoTagProviderCompanyConfiguration.
				confidenceThreshold();

		return Stream.of(
			sentenceDetectorME.sentDetect(_getFileEntryContent(fileEntry))
		).map(
			tokenizerME::tokenize
		).map(
			tokens -> _getTagNames(tokens, confidenceThreshold)
		).flatMap(
			Arrays::stream
		).collect(
			Collectors.toSet()
		);
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

	private static final Log _log = LogFactoryUtil.getLog(
		OpenNLPDocumentAssetAutoTagProvider.class);

	private static final Set<String> _supportedContentTypes = new HashSet<>(
		Arrays.asList(
			"application/epub+zip", "application/vnd.apple.pages.13",
			"application/vnd.google-apps.document",
			"application/vnd.openxmlformats-officedocument.wordprocessingml." +
				"document",
			ContentTypes.APPLICATION_MSWORD, ContentTypes.APPLICATION_PDF,
			ContentTypes.APPLICATION_TEXT, ContentTypes.TEXT,
			ContentTypes.TEXT_PLAIN, ContentTypes.TEXT_HTML,
			ContentTypes.TEXT_HTML_UTF8));

	@Reference
	private ConfigurationProvider _configurationProvider;

	private SentenceModel _sentenceModel;
	private TokenizerModel _tokenizerModel;
	private List<TokenNameFinderModel> _tokenNameFinderModels;

}