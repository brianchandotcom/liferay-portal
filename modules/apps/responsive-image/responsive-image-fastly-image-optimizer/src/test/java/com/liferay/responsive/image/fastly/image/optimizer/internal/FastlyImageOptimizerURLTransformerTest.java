/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.fastly.image.optimizer.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Daniel Sanz
 */
public class FastlyImageOptimizerURLTransformerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		Mockito.when(
			_portal.stripURLAnchor(Mockito.anyString(), Mockito.anyString())
		).thenAnswer(
			input -> _stripURLAnchor(
				(String)input.getArguments()[1],
				(String)input.getArguments()[0])
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	@Test
	public void testTransformAppendsBeforeTheAnchor() {
		Assert.assertEquals(
			_URL + "?width=320#anchor",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL + "#anchor",
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testTransformAppendsToExistingQueryString() {
		Assert.assertEquals(
			_URL + "?version=1.0&dpr=2",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL + "?version=1.0",
				HashMapBuilder.put(
					"dpr", "2"
				).build()));
	}

	@Test
	public void testTransformAppliesSupportedImageTransformation() {
		Assert.assertEquals(
			_URL + "?orient=6",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					"orient", "6"
				).build()));
	}

	@Test
	public void testTransformDoesNotApplyUnsupportedImageTransformations() {
		Assert.assertEquals(
			_URL + "?height=200",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				).put(
					"height", "200"
				).build()));
	}

	@Test
	public void testTransformEncodesValues() {
		Assert.assertEquals(
			_URL + "?bg-color=rgb%281%2C+2%2C+3%29",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					"bg-color", "rgb(1, 2, 3)"
				).build()));
	}

	@Test
	public void testTransformIsDeterministicRegardlessOfImageTransformationsOrder() {
		String format = RandomTestUtil.randomString();
		String quality = RandomTestUtil.randomString();
		String width = RandomTestUtil.randomString();

		Assert.assertEquals(
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				LinkedHashMapBuilder.put(
					"format", format
				).put(
					"quality", quality
				).put(
					"width", width
				).build()),
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				LinkedHashMapBuilder.put(
					"width", width
				).put(
					"quality", quality
				).put(
					"format", format
				).build()));
	}

	@Test
	public void testTransformKeepsSupportedParametersItDoesNotSet() {
		Assert.assertEquals(
			_URL + "?height=200&blur=50&width=320",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL + "?height=200",
				LinkedHashMapBuilder.put(
					"width", "320"
				).put(
					"blur", "50"
				).build()));
	}

	@Test
	public void testTransformKeepsUnsupportedParameters() {
		Assert.assertEquals(
			_URL + "?unsupported=1&width=320",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL + "?unsupported=1",
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testTransformReplacesTheSupportedParameterOfTheSameName() {
		Assert.assertEquals(
			_URL + "?width=320",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL + "?width=" + RandomTestUtil.randomString(),
				HashMapBuilder.put(
					"width", "320"
				).build()));
	}

	@Test
	public void testTransformReturnsURLUnchangedWhenNoImageTransformationIsSupported() {
		Assert.assertEquals(
			_URL,
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					RandomTestUtil.randomString(), RandomTestUtil.randomString()
				).build()));
	}

	@Test
	public void testTransformReturnsURLUnchangedWhenNoImageTransformationsAreGiven() {
		Assert.assertEquals(
			_URL,
			_fastlyImageOptimizerURLTransformer.transform(
				_URL, Collections.<String, String>emptyMap()));

		Assert.assertEquals(
			_URL, _fastlyImageOptimizerURLTransformer.transform(_URL, null));
	}

	@Test
	public void testTransformSkipsBlankNamesAndValues() {
		Assert.assertEquals(
			_URL + "?sharpen=5",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					"", RandomTestUtil.randomString()
				).put(
					"blur", ""
				).put(
					"sharpen", "5"
				).build()));
	}

	@Test
	public void testTransformSortsTransformationsCanonically() {
		Assert.assertEquals(
			_URL + "?format=webp&quality=80&width=320",
			_fastlyImageOptimizerURLTransformer.transform(
				_URL,
				HashMapBuilder.put(
					"format", "webp"
				).put(
					"quality", "80"
				).put(
					"width", "320"
				).build()));
	}

	/**
	 * @see com.liferay.portal.util.PortalImpl
	 *
	 * _stripURLAnchor is copied from PortalImpl for ease of testing.
	 */
	private String[] _stripURLAnchor(String separator, String url) {
		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	private static final String _URL = RandomTestUtil.randomString();

	private final FastlyImageOptimizerURLTransformer
		_fastlyImageOptimizerURLTransformer =
			new FastlyImageOptimizerURLTransformer();

	@Mock
	private Portal _portal;

}