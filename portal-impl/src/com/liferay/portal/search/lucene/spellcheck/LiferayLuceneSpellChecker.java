/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.spellcheck;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.search.spell.SuggestMode;
import org.apache.lucene.search.spell.SuggestWord;
import org.apache.lucene.search.spell.SuggestWordQueue;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.ReaderUtil;
import org.apache.lucene.util.Version;

/**
 * An SpellChecker implementation that allows for multiple languages and
 * groupIds.
 *
 * Implementation is based upon Lucene's
 * org.apache.lucene.search.spell.SpellChecker and uses the NGram algorithm.
 *
 * @author Michael C. Han
 */
public class LiferayLuceneSpellChecker implements SpellChecker {

	public static final float DEFAULT_ACCURACY = 0.5f;

	public LiferayLuceneSpellChecker(Directory spellIndexDirectory)
		throws IOException {

		this(spellIndexDirectory, new LevensteinDistance());
	}

	public LiferayLuceneSpellChecker(
			Directory spellIndexDirectory, StringDistance stringDistance)
		throws IOException {

		this(
			spellIndexDirectory, stringDistance,
			SuggestWordQueue.DEFAULT_COMPARATOR, Version.LUCENE_35);
	}

	public LiferayLuceneSpellChecker(
			Directory spellIndexDirectory, StringDistance stringDistance,
			Comparator<SuggestWord> suggestWordComparator, Version version)
		throws IOException {

		_stringDistance = stringDistance;
		_suggestWordComparator = suggestWordComparator;
		_version = version;

		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		_readLock = readWriteLock.readLock();
		_writeLock = readWriteLock.writeLock();

		setSpellIndexDirectory(spellIndexDirectory);
	}

	public void clearIndex() throws IOException {

		_writeLock.lock();

		try {
			ensureOpen();

			WhitespaceAnalyzer whitespaceAnalyzer = new WhitespaceAnalyzer(
				_version);

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				_version, whitespaceAnalyzer);

			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

			IndexWriter indexWriter = new IndexWriter(
				_spellIndexDirectory, indexWriterConfig);

			indexWriter.close();

			swapSearcher(_spellIndexDirectory);
		}
		finally {
			_writeLock.unlock();
		}
	}

	public void close() throws IOException {
		_readLock.lock();

		try {
			ensureOpen();

			_closed = true;

			if (_indexSearcher != null) {
				_indexSearcher.close();
			}

			_indexSearcher = null;
		}
		finally {
			_readLock.unlock();
		}
	}

	public boolean exist(String word, Locale locale) throws IOException {

		IndexSearcher indexSearcher = obtainSearcher();

		try {
			String localizedFieldName = getLocalizedName(locale, _WORD_FIELD);

			Term term = new Term(localizedFieldName, word);

			if (indexSearcher.docFreq(term) > 0) {
				return true;
			}

			return false;
		}
		finally {
			releaseSearcher(indexSearcher);
		}
	}

	public float getAccuracy() {
		return _accuracy;
	}

	public StringDistance getStringDistance() {
		return _stringDistance;
	}

	public Comparator<SuggestWord> getSuggestWordComparator() {
		return _suggestWordComparator;
	}

	public void indexDictionary(
			Dictionary dictionary, Locale locale,
			IndexWriterConfig indexWriterConfig, boolean fullMerge)
		throws IOException {

		_writeLock.lock();

		try {
			ensureOpen();

			IndexWriter indexWriter = new IndexWriter(
				_spellIndexDirectory, indexWriterConfig);

			IndexSearcher indexSearcher = obtainSearcher();

			List<IndexReader> indexReaders = new ArrayList<IndexReader>();

			if (_indexSearcher.maxDoc() > 0) {
				ReaderUtil.gatherSubReaders(
					indexReaders, _indexSearcher.getIndexReader());
			}

			String localizedFieldName = getLocalizedName(locale, _WORD_FIELD);

			try {
				Iterator<String> wordsIterator = dictionary.getWordsIterator();

				while (wordsIterator.hasNext()) {
					String word = wordsIterator.next();

					indexWord(
						word, localizedFieldName, locale, indexReaders,
						indexWriter);
				}
			}
			finally {
				releaseSearcher(indexSearcher);
			}

			if (fullMerge) {
				indexWriter.forceMerge(1);
			}

			indexWriter.close();

			swapSearcher(_spellIndexDirectory);
		}
		finally {
			_writeLock.unlock();
		}
	}

	public void setAccuracy(float acc) {
		_accuracy = acc;
	}

	public void setBoostEnd(float boostEnd) {
		_boostEnd = boostEnd;
	}

	public void setBoostStart(float boostStart) {
		_boostStart = boostStart;
	}

	public void setSpellIndexDirectory(Directory spellIndexDirectory)
		throws IOException {

		_writeLock.lock();

		try {
			ensureOpen();

			if (!IndexReader.indexExists(spellIndexDirectory)) {
				IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					_version, new WhitespaceAnalyzer(_version));

				IndexWriter writer = new IndexWriter(
					spellIndexDirectory, indexWriterConfig);

				writer.close();
			}

			swapSearcher(spellIndexDirectory);
		}
		finally {
			_writeLock.unlock();
		}
	}

	public void setStringDistance(StringDistance stringDistance) {
		_stringDistance = stringDistance;
	}

	public void setSuggestWordComparator(
		Comparator<SuggestWord> suggestWordComparator) {

		_suggestWordComparator = suggestWordComparator;
	}

	public String[] suggestSimilar(String word, Locale locale, int suggestCount)
		throws IOException {

		return suggestSimilar(
			word, locale, suggestCount, null, null,
			SuggestMode.SUGGEST_WHEN_NOT_IN_INDEX);
	}

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount, float accuracy)
		throws IOException {

		return suggestSimilar(
			word, locale, suggestCount, null, null,
			SuggestMode.SUGGEST_WHEN_NOT_IN_INDEX, accuracy);
	}

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount,
			IndexReader indexReader, String field, SuggestMode suggestMode)
		throws IOException {

		return suggestSimilar(
			word, locale, suggestCount, indexReader, field, suggestMode,
			_accuracy);
	}

	public String[] suggestSimilar(
			String word, Locale locale, int suggestCount,
			IndexReader indexReader, String field, SuggestMode suggestMode,
			float accuracy)
		throws IOException {

		IndexSearcher indexSearcher = obtainSearcher();

		if (locale == null) {
			locale = LocaleUtil.getDefault();

			if (_log.isInfoEnabled()) {
				_log.info(
					"No locale specified, using default locale: " + locale);
			}
		}

		field = getLocalizedName(locale, field);

		try {
			if ((indexReader == null) || (field == null)) {
				suggestMode = SuggestMode.SUGGEST_ALWAYS;
			}

			if (suggestMode == SuggestMode.SUGGEST_ALWAYS) {
				indexReader = null;

				field = null;
			}

			int frequency = 0;

			if ((indexReader != null) && (field != null)) {
				frequency = indexReader.docFreq(new Term(field, word));
			}

			int frequencyGoal = 0;

			if (suggestMode == SuggestMode.SUGGEST_MORE_POPULAR) {
				frequencyGoal = frequency;
			}

			if ((suggestMode == SuggestMode.SUGGEST_WHEN_NOT_IN_INDEX) &&
				(frequency > 0)) {

				return new String[]{ word };
			}

			BooleanQuery suggestWordQuery = buildQuery(word, locale);

			//todo must add locale

			int maxHits = 10 * suggestCount;

			TopDocs topDocs = indexSearcher.search(
				suggestWordQuery, null, maxHits);

			ScoreDoc[] hits = topDocs.scoreDocs;

			SuggestWordQueue suggestWordQueue = new SuggestWordQueue(
				suggestCount, _suggestWordComparator);

			int stop = Math.min(hits.length, maxHits);

			String localizedWordField = getLocalizedName(locale, _WORD_FIELD);

			SuggestWord suggestWord = new SuggestWord();

			for (int i = 0; i < stop; i++) {

				Document document = indexSearcher.doc(hits[i].doc);

				suggestWord.string = document.get(localizedWordField);

				if (suggestWord.string.equals(word)) {
					continue;
				}

				suggestWord.score = _stringDistance.getDistance(
					word, suggestWord.string);

				if (suggestWord.score <= accuracy) {
					continue;
				}

				if ((indexReader != null) && (field != null)) {

					Term suggestWordTerm = new Term(field, suggestWord.string);

					suggestWord.freq = indexReader.docFreq(suggestWordTerm);

					if (((suggestMode == SuggestMode.SUGGEST_MORE_POPULAR) &&
						 (frequencyGoal > suggestWord.freq)) ||
						(suggestWord.freq < 1)) {

						continue;
					}
				}

				suggestWordQueue.insertWithOverflow(suggestWord);

				if (suggestWordQueue.size() == suggestCount) {
					accuracy = suggestWordQueue.top().score;
				}

				suggestWord = new SuggestWord();
			}

			String[] list = new String[suggestWordQueue.size()];

			for (int i = suggestWordQueue.size() - 1; i >= 0; i--) {
				list[i] = suggestWordQueue.pop().string;
			}

			return list;
		}
		finally {
			releaseSearcher(indexSearcher);
		}
	}

	protected void addField(
		Document document, String fieldName, String value,
		Field.Store fieldStore, FieldInfo.IndexOptions indexOptions,
		boolean omitNorms) {

		Field field = new Field(
			fieldName, value, fieldStore, Field.Index.NOT_ANALYZED);

		field.setIndexOptions(indexOptions);

		field.setOmitNorms(omitNorms);

		document.add(field);
	}

	protected void addTermQuery(
		BooleanQuery booleanQuery, String termName, String termValue,
		Float boost, BooleanClause.Occur occur) {

		Query query = new TermQuery(new Term(termName, termValue));

		if (boost != null) {
			query.setBoost(boost);
		}

		BooleanClause booleanClause = new BooleanClause(query, occur);

		booleanQuery.add(booleanClause);
	}

	protected BooleanQuery buildNGramQuery(String word) {
		BooleanQuery booleanQuery = new BooleanQuery();

		String[] nGrams = null;

		int lengthWord = word.length();

		for (int mGramLength = getNGramLengthMin(lengthWord);
				mGramLength <= getNGramLengthMax(lengthWord);
				mGramLength++) {

			String key = "gram" + mGramLength;

			nGrams = formNGrams(word, mGramLength);

			if (nGrams.length == 0) {
				continue;
			}

			if (_boostStart > 0) {
				String startFieldName = "start" + mGramLength;

				addTermQuery(
					booleanQuery, startFieldName, nGrams[0], _boostStart,
					BooleanClause.Occur.SHOULD);

			}

			if (_boostEnd > 0) {
				String endFieldName = "end" + mGramLength;

				addTermQuery(
					booleanQuery, endFieldName, nGrams[nGrams.length - 1],
					_boostEnd, BooleanClause.Occur.SHOULD);

			}

			for (int i = 0; i < nGrams.length; i++) {
				addTermQuery(
					booleanQuery, key, nGrams[i], null,
					BooleanClause.Occur.SHOULD);
			}
		}

		return booleanQuery;
	}

	protected Document createDocument(
		String text, String fieldName, Locale locale, int nGram1, int nGram2) {

		Document document = new Document();

		addField(
			document, fieldName, text, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		if (locale != null) {
			addField(
				document, _LOCALE_FIELD, locale.toString(), Field.Store.NO,
				FieldInfo.IndexOptions.DOCS_ONLY, true);
		}

		int length = text.length();

		for (int nGram = nGram1; nGram <= nGram2; nGram++) {

			String key = "gram" + nGram;

			String end = null;

			for (int i = 0; i < length - nGram + 1; i++) {

				String gram = text.substring(i, i + nGram);

				addField(
					document, key, gram, Field.Store.NO,
					FieldInfo.IndexOptions.DOCS_AND_FREQS, false);

				if (i == 0) {
					addField(
						document, "start" + nGram, gram, Field.Store.NO,
						FieldInfo.IndexOptions.DOCS_ONLY, true);
				}

				end = gram;
			}

			if (end != null) {
				addField(
					document, "end" + nGram, end, Field.Store.NO,
					FieldInfo.IndexOptions.DOCS_ONLY, true);
			}
		}

		return document;
	}

	protected void ensureOpen() {
		if (_closed) {
			throw new AlreadyClosedException("Spellchecker has been closed");
		}
	}

	protected String[] formNGrams(String text, int nGramLength) {
		int length = text.length();

		int numNGrams = length - nGramLength + 1;

		String[] nGrams = new String[numNGrams ];

		for (int i = 0; i < numNGrams; i++) {
			nGrams[i] = text.substring(i, i + nGramLength);
		}

		return nGrams;
	}

	protected String getLocalizedName(Locale locale, String name) {
		if (Validator.isNull(name) || (locale == null)) {
			return name;
		}

		return DocumentImpl.getLocalizedName(locale, name);
	}

	protected int getNGramLengthMax(int length) {
		if (length > 5) {
			return 4;
		}

		if (length == 5) {
			return 3;
		}

		return 2;
	}

	protected int getNGramLengthMin(int length) {
		if (length > 5) {
			return 3;
		}

		if (length == 5) {
			return 2;
		}

		return 1;
	}

	protected void indexWord(
			String word, String localizedFieldName, Locale locale,
			List<IndexReader> indexReaders, IndexWriter indexWriter)
		throws IOException {

		int length = word.length();

		if (length < 3) {
			return;
		}

		if (!indexReaders.isEmpty()) {
			Term term = new Term(localizedFieldName, word);

			for (IndexReader indexReader : indexReaders) {
				if (indexReader.docFreq(term) > 0) {
					continue;
				}
			}
		}

		Document document = createDocument(
			word, localizedFieldName, locale, getNGramLengthMin(length),
			getNGramLengthMax(length));

		indexWriter.addDocument(document);
	}

	protected IndexSearcher obtainSearcher() {
		_readLock.lock();

		try {
			ensureOpen();

			IndexReader indexReader = _indexSearcher.getIndexReader();

			indexReader.incRef();

			return _indexSearcher;
		}
		finally {
			_readLock.unlock();
		}
	}

	protected void releaseSearcher(IndexSearcher indexSearcher)
		throws IOException {

		IndexReader indexReader = indexSearcher.getIndexReader();

		indexReader.decRef();
	}

	protected void swapSearcher(Directory directory) throws IOException {

		IndexReader indexReader = IndexReader.open(directory);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		_readLock.lock();

		try {
			if (_closed) {
				indexSearcher.close();

				throw new AlreadyClosedException(
					"Spellchecker has been _closed");
			}

			if (_indexSearcher != null) {
				_indexSearcher.close();
			}

			_indexSearcher = indexSearcher;

			_spellIndexDirectory = directory;
		}
		finally {
			_readLock.unlock();
		}
	}

	private BooleanQuery buildQuery(String word, Locale locale) {

		BooleanQuery suggestWordQuery = new BooleanQuery();

		BooleanQuery nGramQuery = buildNGramQuery(word);

		BooleanClause booleanNGramQueryClause = new BooleanClause(
			nGramQuery, BooleanClause.Occur.MUST);

		suggestWordQuery.add(booleanNGramQueryClause);

		addTermQuery(
			suggestWordQuery, _LOCALE_FIELD, locale.toString(), null,
			BooleanClause.Occur.MUST);

		return suggestWordQuery;
	}

	private static final String _LOCALE_FIELD = "locale";
	private static final String _WORD_FIELD = "word";

	private static Log _log = LogFactoryUtil.getLog(
		LiferayLuceneSpellChecker.class);

	private float _accuracy = DEFAULT_ACCURACY;
	private float _boostEnd = 1.0f;
	private float _boostStart = 2.0f;

	private volatile boolean _closed = false;

	private IndexSearcher _indexSearcher;
	private Lock _readLock;
	private Directory _spellIndexDirectory;
	private StringDistance _stringDistance;
	private Comparator<SuggestWord> _suggestWordComparator;

	private Version _version;
	private Lock _writeLock;

}