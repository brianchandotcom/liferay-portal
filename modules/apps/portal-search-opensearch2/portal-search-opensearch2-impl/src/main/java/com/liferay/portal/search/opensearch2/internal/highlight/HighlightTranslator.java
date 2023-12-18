/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.highlight;

import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;
import com.liferay.portal.search.query.QueryTranslator;

import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryVariant;
import org.opensearch.client.opensearch.core.search.BoundaryScanner;
import org.opensearch.client.opensearch.core.search.BuiltinHighlighterType;
import org.opensearch.client.opensearch.core.search.HighlightField;
import org.opensearch.client.opensearch.core.search.HighlighterEncoder;
import org.opensearch.client.opensearch.core.search.HighlighterFragmenter;
import org.opensearch.client.opensearch.core.search.HighlighterOrder;
import org.opensearch.client.opensearch.core.search.HighlighterTagsSchema;
import org.opensearch.client.opensearch.core.search.HighlighterType;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
public class HighlightTranslator {

	public org.opensearch.client.opensearch.core.search.Highlight translate(
		Highlight highlight, QueryTranslator<QueryVariant> queryTranslator) {

		org.opensearch.client.opensearch.core.search.Highlight.Builder
			highlightBuilder =
				new org.opensearch.client.opensearch.core.search.Highlight.
					Builder();

		if (ArrayUtil.isNotEmpty(highlight.getBoundaryChars())) {
			highlightBuilder.boundaryChars(
				String.valueOf(highlight.getBoundaryChars()));
		}

		SetterUtil.setNotNullInteger(
			highlightBuilder::boundaryMaxScan, highlight.getBoundaryMaxScan());
		SetterUtil.setNotBlankString(
			highlightBuilder::boundaryScannerLocale,
			highlight.getBoundaryScannerLocale());

		if (highlight.getBoundaryScannerType() != null) {
			highlightBuilder.boundaryScanner(
				_translateBoundaryScannerType(
					highlight.getBoundaryScannerType()));
		}

		if (highlight.getEncoder() != null) {
			highlightBuilder.encoder(_translateEncoder(highlight.getEncoder()));
		}

		for (FieldConfig fieldConfig : highlight.getFieldConfigs()) {
			highlightBuilder.fields(
				fieldConfig.getFieldName(),
				_translateFieldConfigs(fieldConfig, queryTranslator));
		}

		if (highlight.getFragmenter() != null) {
			highlightBuilder.fragmenter(
				_translateFragmenter(highlight.getFragmenter()));
		}

		SetterUtil.setNotNullInteger(
			highlightBuilder::fragmentSize, highlight.getFragmentSize());

		if (highlight.getHighlighterType() != null) {
			highlightBuilder.type(
				_translateHighlighterType(highlight.getHighlighterType()));
		}

		if (highlight.getHighlightQuery() != null) {
			highlightBuilder.highlightQuery(
				new Query(
					queryTranslator.translate(highlight.getHighlightQuery())));
		}

		SetterUtil.setNotNullInteger(
			highlightBuilder::noMatchSize, highlight.getNoMatchSize());
		SetterUtil.setNotNullInteger(
			highlightBuilder::numberOfFragments, highlight.getNumOfFragments());

		if (highlight.getOrder() != null) {
			highlightBuilder.order(_translateOrder(highlight.getOrder()));
		}

		SetterUtil.setNotNullEmptyStringArrayAsList(
			highlightBuilder::preTags, highlight.getPreTags());
		SetterUtil.setNotNullEmptyStringArrayAsList(
			highlightBuilder::postTags, highlight.getPostTags());
		SetterUtil.setNotNullBoolean(
			highlightBuilder::requireFieldMatch,
			highlight.getRequireFieldMatch());

		if (highlight.getTagsSchema() != null) {
			highlightBuilder.tagsSchema(
				_translateTagsSchema(highlight.getTagsSchema()));
		}

		return highlightBuilder.build();
	}

	public org.opensearch.client.opensearch.core.search.Highlight translate(
		String[] highlightFieldNames, int highlightFragmentSize,
		boolean highlightRequireFieldMatch, boolean luceneSyntax,
		int numberOfFragments) {

		if (ArrayUtil.isEmpty(highlightFieldNames)) {
			return null;
		}

		org.opensearch.client.opensearch.core.search.Highlight.Builder
			highlightBuilder =
				new org.opensearch.client.opensearch.core.search.Highlight.
					Builder();

		for (String highlightFieldName : highlightFieldNames) {
			highlightBuilder.fields(
				highlightFieldName,
				HighlightField.of(
					highlightField -> highlightField.fragmentSize(
						highlightFragmentSize
					).numberOfFragments(
						numberOfFragments
					)));
		}

		highlightBuilder.postTags(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		highlightBuilder.preTags(HighlightUtil.HIGHLIGHT_TAG_OPEN);

		if (luceneSyntax) {
			highlightRequireFieldMatch = false;
		}

		highlightBuilder.requireFieldMatch(highlightRequireFieldMatch);

		return highlightBuilder.build();
	}

	private BoundaryScanner _translateBoundaryScannerType(
		String boundaryScannerType) {

		if (boundaryScannerType.equals("chars")) {
			return BoundaryScanner.Chars;
		}
		else if (boundaryScannerType.equals("sentence")) {
			return BoundaryScanner.Sentence;
		}
		else if (boundaryScannerType.equals("word")) {
			return BoundaryScanner.Word;
		}

		throw new IllegalArgumentException(
			"Invalid boundary scanner type " + boundaryScannerType);
	}

	private HighlighterEncoder _translateEncoder(String encoder) {
		if (encoder.equals("default")) {
			return HighlighterEncoder.Default;
		}
		else if (encoder.equals("html")) {
			return HighlighterEncoder.Html;
		}

		throw new IllegalArgumentException(
			"Invalid highlight encoder scanner for " + encoder);
	}

	private HighlightField _translateFieldConfigs(
		FieldConfig fieldConfig,
		QueryTranslator<QueryVariant> queryTranslator) {

		HighlightField.Builder highlightFieldBuilder =
			new HighlightField.Builder();

		if (ArrayUtil.isNotEmpty(fieldConfig.getBoundaryChars())) {
			highlightFieldBuilder.boundaryChars(
				String.valueOf(fieldConfig.getBoundaryChars()));
		}

		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::boundaryMaxScan,
			fieldConfig.getBoundaryMaxScan());
		SetterUtil.setNotBlankString(
			highlightFieldBuilder::boundaryScannerLocale,
			fieldConfig.getBoundaryScannerLocale());

		if (fieldConfig.getBoundaryScannerType() != null) {
			highlightFieldBuilder.boundaryScanner(
				_translateBoundaryScannerType(
					fieldConfig.getBoundaryScannerType()));
		}

		SetterUtil.setNotNullBoolean(
			highlightFieldBuilder::forceSource, fieldConfig.getForceSource());

		if (fieldConfig.getFragmenter() != null) {
			highlightFieldBuilder.fragmenter(
				_translateFragmenter(fieldConfig.getFragmenter()));
		}

		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::fragmentOffset,
			fieldConfig.getFragmentOffset());
		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::fragmentSize, fieldConfig.getFragmentSize());

		if (fieldConfig.getHighlighterType() != null) {
			highlightFieldBuilder.type(
				_translateHighlighterType(fieldConfig.getHighlighterType()));
		}

		if (fieldConfig.getHighlightQuery() != null) {
			highlightFieldBuilder.highlightQuery(
				new Query(
					queryTranslator.translate(
						fieldConfig.getHighlightQuery())));
		}

		SetterUtil.setNotNullEmptyStringArrayAsList(
			highlightFieldBuilder::matchedFields,
			fieldConfig.getMatchedFields());
		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::noMatchSize, fieldConfig.getNoMatchSize());
		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::numberOfFragments,
			fieldConfig.getNumFragments());

		if (fieldConfig.getOrder() != null) {
			highlightFieldBuilder.order(
				_translateOrder(fieldConfig.getOrder()));
		}

		SetterUtil.setNotNullInteger(
			highlightFieldBuilder::phraseLimit, fieldConfig.getPhraseLimit());
		SetterUtil.setNotNullEmptyStringArrayAsList(
			highlightFieldBuilder::postTags, fieldConfig.getPostTags());
		SetterUtil.setNotNullEmptyStringArrayAsList(
			highlightFieldBuilder::preTags, fieldConfig.getPreTags());
		SetterUtil.setNotNullBoolean(
			highlightFieldBuilder::requireFieldMatch,
			fieldConfig.getRequireFieldMatch());

		return highlightFieldBuilder.build();
	}

	private HighlighterFragmenter _translateFragmenter(String fragmenter) {
		if (fragmenter.equals("simple")) {
			return HighlighterFragmenter.Simple;
		}
		else if (fragmenter.equals("span")) {
			return HighlighterFragmenter.Span;
		}

		throw new IllegalArgumentException(
			"No available highlight fragmenter for " + fragmenter);
	}

	private HighlighterType _translateHighlighterType(String highlighterType) {
		BuiltinHighlighterType builtinHighlighterType = null;

		if (highlighterType.equals("FastVector")) {
			builtinHighlighterType = BuiltinHighlighterType.FastVector;
		}
		else if (highlighterType.equals("plain")) {
			builtinHighlighterType = BuiltinHighlighterType.Plain;
		}
		else if (highlighterType.equals("unified")) {
			builtinHighlighterType = BuiltinHighlighterType.Unified;
		}

		if (builtinHighlighterType != null) {
			HighlighterType.Builder highlighterTypeBuilder =
				new HighlighterType.Builder();

			return highlighterTypeBuilder.builtin(
				builtinHighlighterType
			).build();
		}

		throw new IllegalArgumentException(
			"invalid highlighter type " + highlighterType);
	}

	private HighlighterOrder _translateOrder(String order) {
		if (order.equals("score")) {
			return HighlighterOrder.Score;
		}

		throw new IllegalArgumentException(
			"Invalid highlighter order " + order);
	}

	private HighlighterTagsSchema _translateTagsSchema(String tagsSchema) {
		if (tagsSchema.equals("styled")) {
			return HighlighterTagsSchema.Styled;
		}

		throw new IllegalArgumentException("Invalid tags schema" + tagsSchema);
	}

}