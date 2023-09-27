/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.normalizer.internal;

import com.liferay.normalizer.Normalizer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Máté Thurzó
 */
public class NormalizerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAscii() {
		String asciiAlphabet =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		Assert.assertEquals(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
			_normalizer.normalizeToAscii(asciiAlphabet));
	}

	@Test
	public void testGreek() {
		String greekAlphabet = StringBundler.concat(
			"\u0391\u0392\u0393\u0394\u0395\u0396\u0397\u0398\u0399",
			"\u039a\u039b\u039c\u039d\u039e\u039f\u03a0\u03a1\u03a3",
			"\u03a4\u03a5\u03a6\u03a7\u03a8\u03a9\u03b1\u03b2\u03b3",
			"\u03b4\u03b5\u03b6\u03b7\u03b8\u03b9\u03ba\u03bb\u03bc",
			"\u03bd\u03be\u03bf\u03c0\u03c1\u03c2\u03c3\u03c4\u03c5",
			"\u03c6\u03c7\u03c8\u03c9");

		Assert.assertEquals(
			"ABGDEZETHIKLMN'XOPRSTYPHCHPSOabgdezethiklmn'xoprsstyphchpso",
			_normalizer.normalizeToAscii(greekAlphabet));
	}

	@Test
	public void testHungarian() {
		String hungarianAlphabet =
			"A\u00c1BCDE\u00c9FGHI\u00cdJKLMNO\u00d3\u00d6\u0150PQRSTU\u00da" +
				"\u00dc\u0170VWXYZa\u00e1bcde\u00e9fghi\u00edjklmno\u00f3" +
					"\u00f6\u0151pqrstu\u00fa\u00fc\u0171vwxyz";

		Assert.assertEquals(
			"AABCDEEFGHIIJKLMNOOOOPQRSTUUUUVWXYZaabcdeefghiijklmnoooopqrstuuu" +
				"uvwxyz",
			_normalizer.normalizeToAscii(hungarianAlphabet));
	}

	@Test
	public void testRussian() {
		String russianAlphabet = StringBundler.concat(
			"\u0410\u0411\u0412\u0413\u0414\u0415\u0401\u0416\u0417",
			"\u0418\u0419\u041a\u041b\u041c\u041d\u041e\u041f\u0420",
			"\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429",
			"\u042a\u042b\u042c\u042d\u042e\u042f\u0430\u0431\u0432",
			"\u0433\u0434\u0435\u0451\u0436\u0437\u0438\u0439\u043a",
			"\u043b\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443",
			"\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b\u044c",
			"\u044d\u044e\u044f");

		Assert.assertEquals(
			"ABVGDEEZZIJKLMNOPRSTUFHCCSS\"Y'EUAabvgdeezzijklmnoprstufhccss\"y" +
				"'eua",
			_normalizer.normalizeToAscii(russianAlphabet));
	}

	@Test
	public void testSpanish() {
		String spanishAlphabet =
			"ABCDEFGIJKLMN\u00d1OPQRSTUVWXYZabcdefghijklmn\u00f1opqrstuvwxyz";

		Assert.assertEquals(
			"ABCDEFGIJKLMNNOPQRSTUVWXYZabcdefghijklmnnopqrstuvwxyz",
			_normalizer.normalizeToAscii(spanishAlphabet));
	}

	private final Normalizer _normalizer = new NormalizerImpl();

}