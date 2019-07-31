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

package com.liferay.portal.search.elasticsearch6.internal.highlight;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.highlight.FieldConfig;
import com.liferay.portal.search.highlight.Highlight;
import com.liferay.portal.search.query.QueryTranslator;

import java.util.List;
import java.util.stream.Stream;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * @author Michael C. Han
 */
public class HighlightTranslator {

	public HighlightBuilder translate(
		Highlight highlight, QueryTranslator<QueryBuilder> queryTranslator) {

		HighlightBuilder highlightBuilder = new HighlightBuilder();

		List<FieldConfig> fieldConfigs = highlight.getFieldConfigs();

		Stream<FieldConfig> stream = fieldConfigs.stream();

		stream.map(
			this::translate
		).forEach(
			highlightBuilder::field
		);

		if (highlight.getForceSource() != null) {
			highlightBuilder.forceSource(highlight.getForceSource());
		}

		if (highlight.getFragmenter() != null) {
			highlightBuilder.fragmenter(highlight.getFragmenter());
		}

		if (highlight.getFragmentSize() != null) {
			highlightBuilder.fragmentSize(highlight.getFragmentSize());
		}

		if (highlight.getHighlighterType() != null) {
			highlightBuilder.highlighterType(highlight.getHighlighterType());
		}

		if (highlight.getHighlightFilter() != null) {
			highlightBuilder.highlightFilter(highlight.getHighlightFilter());
		}

		if (highlight.getHighlightQuery() != null) {
			highlightBuilder.highlightQuery(
				queryTranslator.translate(highlight.getHighlightQuery()));
		}

		if (highlight.getHighlighterType() != null) {
			highlightBuilder.highlighterType(highlight.getHighlighterType());
		}

		if (highlight.getNumOfFragments() != null) {
			highlightBuilder.numOfFragments(highlight.getNumOfFragments());
		}

		if (ArrayUtil.isNotEmpty(highlight.getPreTags())) {
			highlightBuilder.preTags(highlight.getPreTags());
		}

		if (ArrayUtil.isNotEmpty(highlight.getPostTags())) {
			highlightBuilder.postTags(highlight.getPostTags());
		}

		if (highlight.getRequireFieldMatch() != null) {
			highlightBuilder.requireFieldMatch(
				highlight.getRequireFieldMatch());
		}

		return highlightBuilder;
	}

	protected HighlightBuilder.Field translate(FieldConfig fieldConfig) {
		HighlightBuilder.Field field = new HighlightBuilder.Field(
			fieldConfig.getField());

		if (fieldConfig.getFragmentOffset() != null) {
			field.fragmentOffset(fieldConfig.getFragmentOffset());
		}

		if (fieldConfig.getFragmentSize() != null) {
			field.fragmentSize(fieldConfig.getFragmentSize());
		}

		if (fieldConfig.getNumFragments() != null) {
			field.numOfFragments(fieldConfig.getNumFragments());
		}

		return field;
	}

}