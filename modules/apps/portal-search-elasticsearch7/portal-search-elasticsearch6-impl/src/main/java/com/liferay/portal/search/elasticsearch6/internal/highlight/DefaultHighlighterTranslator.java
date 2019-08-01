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

import com.liferay.portal.kernel.search.highlight.HighlightUtil;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = HighlighterTranslator.class)
public class DefaultHighlighterTranslator implements HighlighterTranslator {

	@Override
	public void translate(
		SearchRequestBuilder searchRequestBuilder, String[] highlightFieldNames,
		boolean highlightRequireFieldMatch, int highlightFragmentSize,
		int highlightSnippetSize, boolean luceneSyntax) {

		HighlightBuilder highlightBuilder = new HighlightBuilder();

		for (String highlightFieldName : highlightFieldNames) {
			highlightBuilder.field(
				highlightFieldName, highlightFragmentSize,
				highlightSnippetSize);
		}

		highlightBuilder.postTags(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		highlightBuilder.preTags(HighlightUtil.HIGHLIGHT_TAG_OPEN);

		if (luceneSyntax) {
			highlightRequireFieldMatch = false;
		}

		highlightBuilder.requireFieldMatch(highlightRequireFieldMatch);

		searchRequestBuilder.highlighter(highlightBuilder);
	}

}