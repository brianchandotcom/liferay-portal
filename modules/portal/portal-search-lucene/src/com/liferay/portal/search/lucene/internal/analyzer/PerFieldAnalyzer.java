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

package com.liferay.portal.search.lucene.internal.analyzer;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.QueryPreProcessConfiguration;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Tokenizer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ar.ArabicAnalyzer;
import org.apache.lucene.analysis.bg.BulgarianAnalyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.ca.CatalanAnalyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cz.CzechAnalyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.eu.BasqueAnalyzer;
import org.apache.lucene.analysis.fa.PersianAnalyzer;
import org.apache.lucene.analysis.fi.FinnishAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.gl.GalicianAnalyzer;
import org.apache.lucene.analysis.hi.HindiAnalyzer;
import org.apache.lucene.analysis.hu.HungarianAnalyzer;
import org.apache.lucene.analysis.id.IndonesianAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.analysis.nl.DutchAnalyzer;
import org.apache.lucene.analysis.no.NorwegianAnalyzer;
import org.apache.lucene.analysis.pt.PortugueseAnalyzer;
import org.apache.lucene.analysis.ro.RomanianAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.sv.SwedishAnalyzer;
import org.apache.lucene.analysis.th.ThaiAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tr.TurkishAnalyzer;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.util.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Raymond Augé
 * @author Mate Thurzo
 */
@Component(
	immediate = true, property = {"version=LUCENE_35"},
	service = {
		Analyzer.class, QueryPreProcessConfiguration.class, Tokenizer.class
	}
)
public class PerFieldAnalyzer extends Analyzer
	implements QueryPreProcessConfiguration, Tokenizer {

	@Activate
	public void activate(ComponentContext componentContext) {
		Dictionary<String, Object> dictionary =
			componentContext.getProperties();

		_version = Version.valueOf(
			GetterUtil.getString(dictionary.get("version"), "LUCENE_35"));

		BundleContext bundleContext = componentContext.getBundleContext();

		_serviceRegistration = bundleContext.registerService(
			Version.class, _version, new Hashtable<String, Object>());

		initializeAnalyzers();
	}

	public void addAnalyzer(String fieldName, Analyzer analyzer) {
		_analyzers.put(
			fieldName,
			new ObjectValuePair<>(Pattern.compile(fieldName), analyzer));
	}

	public Analyzer getAnalyzer(String fieldName) {
		ObjectValuePair<Pattern, Analyzer> objectValuePair = _analyzers.get(
			fieldName);

		if (objectValuePair != null) {
			return objectValuePair.getValue();
		}

		for (ObjectValuePair<Pattern, Analyzer> curObjectValuePair :
				_analyzers.values()) {

			Pattern pattern = curObjectValuePair.getKey();

			Matcher matcher = pattern.matcher(fieldName);

			if (matcher.matches()) {
				return curObjectValuePair.getValue();
			}
		}

		return _analyzer;
	}

	@Override
	public int getOffsetGap(Fieldable field) {
		Analyzer analyzer = getAnalyzer(field.name());

		return analyzer.getOffsetGap(field);
	}

	@Override
	public int getPositionIncrementGap(String fieldName) {
		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.getPositionIncrementGap(fieldName);
	}

	@Override
	public boolean isSubstringSearchAlways(String fieldName) {
		Analyzer analyzer = getAnalyzer(fieldName);

		if (analyzer instanceof LikeKeywordAnalyzer) {
			return true;
		}

		return false;
	}

	@Override
	public final TokenStream reusableTokenStream(
			String fieldName, Reader reader)
		throws IOException {

		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.reusableTokenStream(fieldName, reader);
	}

	@Override
	public List<String> tokenize(
			String fieldName, String input, String languageId)
		throws SearchException {

		List<String> tokens = new ArrayList<>();
		TokenStream tokenStream = null;

		try {
			String localizedFieldName = DocumentImpl.getLocalizedName(
				languageId, fieldName);

			Analyzer analyzer = getAnalyzer(localizedFieldName);

			tokenStream = analyzer.tokenStream(
				localizedFieldName, new StringReader(input));

			CharTermAttribute charTermAttribute = tokenStream.addAttribute(
				CharTermAttribute.class);

			tokenStream.reset();

			while (tokenStream.incrementToken()) {
				tokens.add(charTermAttribute.toString());
			}

			tokenStream.end();
		}
		catch (IOException ioe) {
			throw new SearchException(ioe);
		}
		finally {
			if (tokenStream != null) {
				try {
					tokenStream.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to close token stream", ioe);
					}
				}
			}
		}

		return tokens;
	}

	@Override
	public final TokenStream tokenStream(String fieldName, Reader reader) {
		Analyzer analyzer = getAnalyzer(fieldName);

		return analyzer.tokenStream(fieldName, reader);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	protected void initializeAnalyzers() {
		_analyzer = new StandardAnalyzer(_version);

		LikeKeywordAnalyzer likeKeywordAnalyzer = new LikeKeywordAnalyzer();
		UpperCaseKeywordAnalyzer upperCaseKeywordAnalyzer =
			new UpperCaseKeywordAnalyzer();
		KeywordAnalyzer keywordAnalyzer = new KeywordAnalyzer();

		addAnalyzer("articleId", upperCaseKeywordAnalyzer);
		addAnalyzer("assetCategoryTitles?(_.+)?", likeKeywordAnalyzer);
		addAnalyzer("assetTagNames", likeKeywordAnalyzer);
		addAnalyzer("ddm/.*", keywordAnalyzer);
		addAnalyzer("ddmStructureKey", keywordAnalyzer);
		addAnalyzer("ddmTemplateKey", keywordAnalyzer);
		addAnalyzer("emailAddress", likeKeywordAnalyzer);
		addAnalyzer("entryClassName", keywordAnalyzer);
		addAnalyzer("entryClassPK", keywordAnalyzer);
		addAnalyzer("extension", keywordAnalyzer);
		addAnalyzer("firstName", likeKeywordAnalyzer);
		addAnalyzer("groupId", keywordAnalyzer);
		addAnalyzer("groupRoleId", keywordAnalyzer);
		addAnalyzer("installedVersion", keywordAnalyzer);
		addAnalyzer("lastName", likeKeywordAnalyzer);
		addAnalyzer("layoutUuid", keywordAnalyzer);
		addAnalyzer("license", likeKeywordAnalyzer);
		addAnalyzer("middleName", likeKeywordAnalyzer);
		addAnalyzer("path", likeKeywordAnalyzer);
		addAnalyzer("roleId", keywordAnalyzer);
		addAnalyzer("rootEntryClassPK", keywordAnalyzer);
		addAnalyzer("screenName", likeKeywordAnalyzer);
		addAnalyzer("status", keywordAnalyzer);
		addAnalyzer("structureId", keywordAnalyzer);
		addAnalyzer("tag", likeKeywordAnalyzer);
		addAnalyzer("templateId", keywordAnalyzer);
		addAnalyzer("treePath", likeKeywordAnalyzer);
		addAnalyzer("type", keywordAnalyzer);
		addAnalyzer("userId", keywordAnalyzer);
		addAnalyzer("userName", likeKeywordAnalyzer);

		addAnalyzer(".*_ar_SA", new ArabicAnalyzer(_version));
		addAnalyzer(".*_bg_BG", new BulgarianAnalyzer(_version));
		addAnalyzer(".*_ca_[A-Z]{2}", new CatalanAnalyzer(_version));
		addAnalyzer(".*_cz_CZ", new CzechAnalyzer(_version));
		addAnalyzer(".*_da_DK", new GermanAnalyzer(_version));
		addAnalyzer(".*_de_DE", new GermanAnalyzer(_version));
		addAnalyzer(".*_el_GR", new GreekAnalyzer(_version));
		addAnalyzer(".*_es_[A-Z]{2}", new SpanishAnalyzer(_version));
		addAnalyzer(".*_eu_ES", new BasqueAnalyzer(_version));
		addAnalyzer(".*_fa_IR", new PersianAnalyzer(_version));
		addAnalyzer(".*_fi_FI", new FinnishAnalyzer(_version));
		addAnalyzer(".*_fr_[A-Z]{2}", new FrenchAnalyzer(_version));
		addAnalyzer(".*_gl_ES", new GalicianAnalyzer(_version));
		addAnalyzer(".*_hi_IN", new HindiAnalyzer(_version));
		addAnalyzer(".*_hu_HU", new HungarianAnalyzer(_version));
		addAnalyzer(".*_in_ID", new IndonesianAnalyzer(_version));
		addAnalyzer(".*_it_IT", new ItalianAnalyzer(_version));
		addAnalyzer(".*_ja_JP", new CJKAnalyzer(_version));
		addAnalyzer(".*_ko_KR", new CJKAnalyzer(_version));
		addAnalyzer(".*_nl_NL", new DutchAnalyzer(_version));
		addAnalyzer(".*_nb_NO", new NorwegianAnalyzer(_version));
		addAnalyzer(".*_pt_BR", new BrazilianAnalyzer(_version));
		addAnalyzer(".*_pt_PT", new PortugueseAnalyzer(_version));
		addAnalyzer(".*_ro_RO", new RomanianAnalyzer(_version));
		addAnalyzer(".*_ru_RU", new RussianAnalyzer(_version));
		addAnalyzer(".*_sv_SE", new SwedishAnalyzer(_version));
		addAnalyzer(".*_th_TH", new ThaiAnalyzer(_version));
		addAnalyzer(".*_tr_TR", new TurkishAnalyzer(_version));
		addAnalyzer(".*_zh_CN", new CJKAnalyzer(_version));
		addAnalyzer(".*_zh_TW", new CJKAnalyzer(_version));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PerFieldAnalyzer.class);

	private Analyzer _analyzer;
	private final Map<String, ObjectValuePair<Pattern, Analyzer>> _analyzers =
		new LinkedHashMap<>();
	private ServiceRegistration<Version> _serviceRegistration;
	private Version _version;

}