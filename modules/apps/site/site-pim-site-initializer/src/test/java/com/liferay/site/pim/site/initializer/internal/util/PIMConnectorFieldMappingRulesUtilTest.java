/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.util;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParserUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.pim.site.initializer.connector.PIMConnectorChannelField;
import com.liferay.site.pim.site.initializer.constants.PIMConnectorFieldMappingTypeConstants;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Balazs Breier
 */
public class PIMConnectorFieldMappingRulesUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		_htmlParserUtilMockedStatic.close();
	}

	@Test
	public void testApplyRules() {
		_htmlParserUtilMockedStatic.when(
			() -> HtmlParserUtil.extractText("<p> Red <b>Shirt</b> </p>")
		).thenReturn(
			" Red Shirt "
		);

		Assert.assertEquals(
			"RED SHIRT",
			PIMConnectorFieldMappingRulesUtil.applyRules(
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"stripHTMLTags", true
					).put(
						"textCase", "upper"
					).put(
						"trimWhitespace", true
					).build()),
				"<p> Red <b>Shirt</b> </p>"));
		Assert.assertEquals(
			"caf%C3%A9",
			PIMConnectorFieldMappingRulesUtil.applyRules(
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"textCase", "lower"
					).put(
						"urlEncode", true
					).build()),
				"CafÉ"));
		Assert.assertEquals(
			"Fjord Lounge Chair with Solid Oak...",
			PIMConnectorFieldMappingRulesUtil.applyRules(
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"truncateLength", 40
					).build()),
				"Fjord Lounge Chair with Solid Oak Frame and Matching " +
					"Footstool"));
		Assert.assertEquals(
			"abcdefghijklmnopqrstuvwxyz0123456789...",
			PIMConnectorFieldMappingRulesUtil.applyRules(
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"truncateLength", 40
					).put(
						"urlEncode", true
					).build()),
				"abcdefghijklmnopqrstuvwxyz0123456789ééé"));
		Assert.assertNull(
			PIMConnectorFieldMappingRulesUtil.applyRules(
				_mockObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"stripHTMLTags", true
					).build()),
				null));
	}

	@Test
	public void testIsMappingRulesSupported() {
		Assert.assertFalse(
			PIMConnectorFieldMappingRulesUtil.isMappingRulesSupported(null));
		Assert.assertFalse(
			PIMConnectorFieldMappingRulesUtil.isMappingRulesSupported(
				new PIMConnectorChannelField(
					Collections.emptyList(), RandomTestUtil.randomString(),
					true, RandomTestUtil.randomString(), false,
					ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER)));
		Assert.assertTrue(
			PIMConnectorFieldMappingRulesUtil.isMappingRulesSupported(
				new PIMConnectorChannelField(
					Collections.emptyList(), RandomTestUtil.randomString(),
					true, RandomTestUtil.randomString(), false,
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));
	}

	@Test
	public void testIsUseFirstNonemptySourceSupported() {
		Assert.assertFalse(
			PIMConnectorFieldMappingRulesUtil.isUseFirstNonemptySourceSupported(
				null));
		Assert.assertTrue(
			PIMConnectorFieldMappingRulesUtil.isUseFirstNonemptySourceSupported(
				new PIMConnectorChannelField(
					Collections.emptyList(), RandomTestUtil.randomString(),
					false, RandomTestUtil.randomString(), false,
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));
		Assert.assertFalse(
			PIMConnectorFieldMappingRulesUtil.isUseFirstNonemptySourceSupported(
				new PIMConnectorChannelField(
					Collections.emptyList(), RandomTestUtil.randomString(),
					true, RandomTestUtil.randomString(), false,
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));
		Assert.assertTrue(
			PIMConnectorFieldMappingRulesUtil.isUseFirstNonemptySourceSupported(
				new PIMConnectorChannelField(
					Arrays.asList(
						PIMConnectorFieldMappingTypeConstants.CATEGORIES),
					RandomTestUtil.randomString(), true,
					RandomTestUtil.randomString(), false,
					ObjectFieldConstants.BUSINESS_TYPE_TEXT)));
	}

	private ObjectEntry _mockObjectEntry(Map<String, Serializable> values) {
		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getValues()
		).thenReturn(
			values
		);

		return objectEntry;
	}

	private final MockedStatic<HtmlParserUtil> _htmlParserUtilMockedStatic =
		Mockito.mockStatic(HtmlParserUtil.class);

}