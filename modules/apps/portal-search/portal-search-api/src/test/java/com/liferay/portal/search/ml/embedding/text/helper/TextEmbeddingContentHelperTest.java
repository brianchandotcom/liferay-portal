/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.ml.embedding.text.helper;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.search.ml.embedding.text.TextEmbeddingDocumentContributor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class TextEmbeddingContentHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		TextEmbeddingDocumentContributor textEmbeddingDocumentContributor =
			Mockito.mock(TextEmbeddingDocumentContributor.class);

		Mockito.doReturn(
			List.of("en_US", "pt_BR")
		).when(
			textEmbeddingDocumentContributor
		).getLanguageIds(
			Mockito.any()
		);

		_textEmbeddingContentHelper = new TextEmbeddingContentHelper<>(
			1L, "en_US", _DELIMITER, Mockito.mock(TestBaseModel.class), 10,
			textEmbeddingDocumentContributor);
	}

	@Test
	public void testAppend1() {
		_textEmbeddingContentHelper.append("all");
		_textEmbeddingContentHelper.append("en_US", "localized_en_US");
		_textEmbeddingContentHelper.append("pt_BR", "localized_pt_BR");

		Assert.assertEquals(
			StringBundler.concat("all", _DELIMITER, "localized_en_US"),
			_textEmbeddingContentHelper.getLocalizedContent("en_US"));
		Assert.assertEquals(
			StringBundler.concat("all", _DELIMITER, "localized_pt_BR"),
			_textEmbeddingContentHelper.getLocalizedContent("pt_BR"));

		Map<String, String> localizedContentMap =
			_textEmbeddingContentHelper.getLocalizedContentMap();

		Assert.assertEquals(
			StringBundler.concat("all", _DELIMITER, "localized_en_US"),
			localizedContentMap.get("en_US"));
		Assert.assertEquals(
			StringBundler.concat("all", _DELIMITER, "localized_pt_BR"),
			localizedContentMap.get("pt_BR"));

		Assert.assertEquals(
			"all", _textEmbeddingContentHelper.getNonlocalizedContent());
	}

	@Test
	public void testAppend2() {
		_textEmbeddingContentHelper.append("en_US", "alpha");

		Map<String, String> localizedContentMap =
			_textEmbeddingContentHelper.getLocalizedContentMap();

		Assert.assertEquals("alpha", localizedContentMap.get("en_US"));

		_textEmbeddingContentHelper.append("en_US", "beta");

		localizedContentMap =
			_textEmbeddingContentHelper.getLocalizedContentMap();

		Assert.assertEquals(
			StringBundler.concat("alpha", _DELIMITER, "beta"),
			localizedContentMap.get("en_US"));
	}

	@Test
	public void testAppend3() {
		_textEmbeddingContentHelper.append("all");
		_textEmbeddingContentHelper.append("en_US", "localized_en_US");

		Assert.assertEquals(
			_textEmbeddingContentHelper.getLocalizedContent("en_US"),
			_textEmbeddingContentHelper.getLocalizedContent("pt_BR"));
	}

	private static final String _DELIMITER = StringPool.COMMA_AND_SPACE;

	private TextEmbeddingContentHelper<TestBaseModel>
		_textEmbeddingContentHelper;

	private interface TestBaseModel extends BaseModel<TestBaseModel> {
	}

}