/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public class LuceneHelperUtil {

	public static void addDate(Document doc, String field, Date value) {
		doc.add(LuceneFields.getDate(field, value));
	}

	public static void addDocument(long companyId, Document document)
		throws IOException {

		getLuceneHelper().addDocument(companyId, document);
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addExactTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		getLuceneHelper().addExactTerm(booleanQuery, field, value);
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, int startValue, int endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, long startValue,
		long endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, short startValue,
		short endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue) {

		getLuceneHelper().addNumericRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, int startValue, int endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, Integer startValue,
		Integer endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, long startValue,
		long endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, Long startValue,
		Long endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, short startValue,
		short endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, Short startValue,
		Short endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, String.valueOf(startValue),
			String.valueOf(endValue));
	}

	public static void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		getLuceneHelper().addRangeTerm(
			booleanQuery, field, startValue, endValue);
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, boolean value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, double value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, int value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, short value) {

		addRequiredTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addRequiredTerm(booleanQuery, field, value, false);
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		getLuceneHelper().addRequiredTerm(booleanQuery, field, value, like);
	}

	public static void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		getLuceneHelper().addRequiredTerm(booleanQuery, field, values, like);
	}

	public static void addTerm(
		BooleanQuery booleanQuery, String field, long value) {

		addTerm(booleanQuery, field, String.valueOf(value));
	}

	public static void addTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addTerm(booleanQuery, field, value, false);
	}

	public static void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		getLuceneHelper().addTerm(booleanQuery, field, value, like);
	}

	public static void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur) {

		getLuceneHelper().addTerm(
			booleanQuery, field, value, like, booleanClauseOccur);
	}

	public static void addTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		getLuceneHelper().addTerm(booleanQuery, field, values, like);
	}

	public static void closeSearcher(IndexSearcher indexSearcher) {
		getLuceneHelper().closeSearcher(indexSearcher);
	}

	public static int countScoredFieldNames(Query query, String[] fieldNames) {
		return getLuceneHelper().countScoredFieldNames(query, fieldNames);
	}

	public static void delete(long companyId) {
		getLuceneHelper().delete(companyId);
	}

	public static void deleteDocuments(long companyId, Term term)
		throws IOException {

		getLuceneHelper().deleteDocuments(companyId, term);
	}

	public static void dumpIndex(long companyId, OutputStream outputStream)
		throws IOException {

		getLuceneHelper().dumpIndex(companyId, outputStream);
	}

	public static Analyzer getAnalyzer() {
		return getLuceneHelper().getAnalyzer();
	}

	public static IndexAccessor getIndexAccessor(long companyId) {
		return getLuceneHelper().getIndexAccessor(companyId);
	}

	public static long getLastGeneration(long companyId) {
		return getLuceneHelper().getLastGeneration(companyId);
	}

	public static InputStream getLoadIndexesInputStreamFromCluster(
			long companyId, Address bootupAddress)
		throws SystemException {

		return getLuceneHelper().getLoadIndexesInputStreamFromCluster(
			companyId, bootupAddress);
	}

	public static LuceneHelper getLuceneHelper() {
		return _luceneHelper;
	}

	public static Set<String> getQueryTerms(Query query) {
		return getLuceneHelper().getQueryTerms(query);
	}

	public static IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		return getLuceneHelper().getSearcher(companyId, readOnly);
	}

	public static String getSnippet(Query query, String field, String s)
		throws IOException {

		Formatter formatter = new SimpleHTMLFormatter(
			StringPool.BLANK, StringPool.BLANK);

		return getSnippet(query, field, s, formatter);
	}

	public static String getSnippet(
			Query query, String field, String s, Formatter formatter)
		throws IOException {

		return getSnippet(query, field, s, 3, 80, "...", formatter);
	}

	public static String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, Formatter formatter)
		throws IOException {

		return getLuceneHelper().getSnippet(
			query, field, s, maxNumFragments, fragmentLength, fragmentSuffix,
			formatter);
	}

	public static Version getVersion() {
		return getLuceneHelper().getVersion();
	}

	public static boolean isLoadIndexFromClusterEnabled() {
		return getLuceneHelper().isLoadIndexFromClusterEnabled();
	}

	public static void loadIndex(long companyId, InputStream inputStream)
		throws IOException {

		getLuceneHelper().loadIndex(companyId, inputStream);
	}

	public static void loadIndexesFromCluster(long companyId)
		throws SystemException {

		getLuceneHelper().loadIndexesFromCluster(companyId);
	}

	public static void shutdown() {
		getLuceneHelper().shutdown();
	}

	public static void shutdown(long companyId) {
		getLuceneHelper().shutdown(companyId);
	}

	public static void startup(long companyId) {
		getLuceneHelper().startup(companyId);
	}

	public static void updateDocument(
			long companyId, Term term, Document document)
		throws IOException {

		getLuceneHelper().updateDocument(companyId, term, document);
	}

	public void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	private static LuceneHelper _luceneHelper;

}