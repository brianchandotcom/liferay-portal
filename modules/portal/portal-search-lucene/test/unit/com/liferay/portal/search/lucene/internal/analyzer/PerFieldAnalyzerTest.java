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

import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Fieldable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Mate Thurzo
 */
public class PerFieldAnalyzerTest extends PowerMockito {

	@Before
	public void setUp() {
		_perFieldAnalyzer = new PerFieldAnalyzer();

		Dictionary<String, Object> hashMapDictionary =
			new HashMapDictionary<>();

		hashMapDictionary.put("version", "LUCENE_35");

		_perFieldAnalyzer.activate(new MockComponentContext(hashMapDictionary));
	}

	@Test
	public void testAddAnalyzer() throws Exception {
		Analyzer analyzer = mock(Analyzer.class);

		String fieldName = "testFieldName";

		when(
			analyzer.getPositionIncrementGap(fieldName)
		).thenReturn(
			1
		);

		when(
			analyzer.getOffsetGap(Mockito.any(Fieldable.class))
		).thenReturn(
			1
		);

		Fieldable fieldable = mock(Fieldable.class);

		when(
			fieldable.name()
		).thenReturn(
			fieldName
		);

		_perFieldAnalyzer.addAnalyzer(fieldName, analyzer);

		int positionIncrementGap = _perFieldAnalyzer.getPositionIncrementGap(
			fieldName);

		Assert.assertEquals(
			analyzer.getPositionIncrementGap(fieldName), positionIncrementGap);

		int offsetGap = _perFieldAnalyzer.getOffsetGap(fieldable);

		Assert.assertEquals(analyzer.getOffsetGap(fieldable), offsetGap);
	}

	private PerFieldAnalyzer _perFieldAnalyzer;

}