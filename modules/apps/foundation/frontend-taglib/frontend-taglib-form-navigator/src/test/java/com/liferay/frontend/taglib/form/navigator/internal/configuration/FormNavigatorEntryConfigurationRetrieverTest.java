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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Enclosed.class)
public class FormNavigatorEntryConfigurationRetrieverTest {

	public static class WhenAConfigurationEntryHasOneLineWithNoKeys {

		@Before
		public void setUp() throws Exception {
			_setMockConfigurations(
				_createMockConfig("form1", new String[] {"add.general="}));
		}

		@Test
		public void testReturnsEmptyList() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

	}

	public static class WhenAConfigurationEntryHasSeveralLines {

		@Before
		public void setUp() throws Exception {
			StringBundler sb1 = new StringBundler(5);

			sb1.append("add.general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey3");

			StringBundler sb2 = new StringBundler(5);

			sb2.append("update.general");
			sb2.append(StringPool.EQUAL);
			sb2.append("formNavigatorEntryKey1,");
			sb2.append("formNavigatorEntryKey4,");
			sb2.append("formNavigatorEntryKey5");

			String config = sb1.toString() + "\n" + sb2.toString();

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {config}));
		}

		@Test
		public void testContainsValuesForLine1() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey3", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testContainsValuesForLine2() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "update").
					get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

	}

	public static class WhenAKeyHasLeadingOrTrailingSpaces {

		@Before
		public void setUp() throws Exception {
			StringBundler sb = new StringBundler(4);

			sb.append("add.general");
			sb.append(StringPool.EQUAL);
			sb.append("  formNavigatorEntryKey1,   ");
			sb.append("formNavigatorEntryKey2  ");

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {sb.toString()}));
		}

		@Test
		public void testTheyAreTrimmed() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
		}

	}

	public static class WhenNoContextIsSet {

		@Before
		public void setUp() throws Exception {
			StringBundler sb1 = new StringBundler(4);

			sb1.append("general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2");

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {sb1.toString()}));
		}

		@Test
		public void testReturnsTheKeysInThatLineWhenAskedForAContext() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
		}

		@Test
		public void testReturnsTheKeysInThatLineWhenAskedForNoConext() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", null).get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
		}

	}

	public static class WhenThereAreSeveralConfigurations {

		@Before
		public void setUp() throws Exception {
			StringBundler sb1 = new StringBundler(5);

			sb1.append("add.general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey3");

			StringBundler sb2 = new StringBundler(5);

			sb2.append("update.general");
			sb2.append(StringPool.EQUAL);
			sb2.append("formNavigatorEntryKey1,");
			sb2.append("formNavigatorEntryKey4,");
			sb2.append("formNavigatorEntryKey5");

			_setMockConfigurations(
				_createMockConfig("form1", new String[] {sb1.toString()}),
				_createMockConfig("form1", new String[] {sb2.toString()}));
		}

		@Test
		public void testContainsValuesForEntry1() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey2", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey3", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testContainsValuesForEntry2() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "update").
					get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownCategory() {
			Optional<List<String>> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys(
						"form1", "unknownCategory", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownContext() {
			Optional<List<String>> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys(
						"form1", "general", "unknownContext");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownFormId() {
			Optional<List<String>> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("unknownForm", "general", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

	}

	public static class WhenThereIsNoConfigAtAll {

		@Before
		public void setUp() throws Exception {
			_formNavigatorEntryConfigurationRetriever =
				new FormNavigatorEntryConfigurationRetriever();
		}

		@Test
		public void testReturnsEmptyOptional() {
			Optional<List<String>> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

	}

	public static class WhenThereIsOneConfigurationWithTwoLinesForSameTarget {

		@Before
		public void setUp() throws Exception {
			StringBundler sb1 = new StringBundler(5);

			sb1.append("add.general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey3");

			StringBundler sb2 = new StringBundler(5);

			sb2.append("add.general");
			sb2.append(StringPool.EQUAL);
			sb2.append("formNavigatorEntryKey1,");
			sb2.append("formNavigatorEntryKey4,");
			sb2.append("formNavigatorEntryKey5");

			_setMockConfigurations(
				_createMockConfig(
					"form1", new String[] {sb1.toString(), sb2.toString()}));
		}

		@Test
		public void testTheLastOneHasPrecedence() {
			List<String> formNavigatorEntryKeys =
				_formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());
			Assert.assertEquals(
				"formNavigatorEntryKey1", formNavigatorEntryKeys.get(0));
			Assert.assertEquals(
				"formNavigatorEntryKey4", formNavigatorEntryKeys.get(1));
			Assert.assertEquals(
				"formNavigatorEntryKey5", formNavigatorEntryKeys.get(2));
		}

	}

	private static FormNavigatorEntryConfigurationParser _createMockConfig(
		String formNavigatorId, String[] formNavigatorEntryKeys) {

		Map<String, Object> properties = new HashMap<>();

		properties.put("formNavigatorEntryKeys", formNavigatorEntryKeys);
		properties.put("formNavigatorId", formNavigatorId);

		FormNavigatorEntryConfigurationParser
			formNavigatorEntryConfigurationParser =
				new FormNavigatorEntryConfigurationParser();

		formNavigatorEntryConfigurationParser.activate(properties);

		return formNavigatorEntryConfigurationParser;
	}

	private static void _setMockConfigurations(
			FormNavigatorEntryConfigurationParser... parsers)
		throws Exception {

		for (FormNavigatorEntryConfigurationParser parser : parsers) {
			_formNavigatorEntryConfigurationRetriever.
				setFormNavigatorEntryConfigurationParser(parser);
		}
	}

	private static FormNavigatorEntryConfigurationRetriever
		_formNavigatorEntryConfigurationRetriever =
			new FormNavigatorEntryConfigurationRetriever();

}