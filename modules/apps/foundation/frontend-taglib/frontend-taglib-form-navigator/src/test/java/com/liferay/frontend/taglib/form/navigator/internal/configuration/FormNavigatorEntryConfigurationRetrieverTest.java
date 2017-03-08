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

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Enclosed.class)
public class FormNavigatorEntryConfigurationRetrieverTest {

	public static class WhenAConfigurationEntryHasDuplicates
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

			StringBundler sb1 = new StringBundler(5);

			sb1.append("add.general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey4,");
			sb1.append("formNavigatorEntryKey3,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey3,");
			sb1.append("formNavigatorEntryKey4");

			createConfiguration("form1", new String[] {sb1.toString()});
		}

		@Test
		public void testOnlyTheFirstOcurrenceIsRetrieved() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 4,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey3", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
		}

	}

	public static class WhenAConfigurationEntryHasOneLineWithNoKeys
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

			createConfiguration("form1", new String[] {"add.general="});
		}

		@Test
		public void testReturnsEmptyList() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertTrue(formNavigatorEntryKeys.isEmpty());
		}

	}

	public static class WhenAConfigurationEntryHasSeveralLines
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

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

			createConfiguration("form1", new String[] {config});
		}

		@Test
		public void testContainsValuesForLine1() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey3", iterator.next());
		}

		@Test
		public void testContainsValuesForLine2() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "update").
					get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey5", iterator.next());
		}

	}

	public static class WhenAKeyHasLeadingOrTrailingSpaces
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general");
			sb.append(StringPool.EQUAL);
			sb.append("  formNavigatorEntryKey1,   ");
			sb.append("formNavigatorEntryKey2  ");

			createConfiguration("form1", new String[] {sb.toString()});
		}

		@Test
		public void testTheyAreTrimmed() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
		}

	}

	public static class WhenNoContextIsSet
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2");

			createConfiguration("form1", new String[] {sb1.toString()});
		}

		@Test
		public void testReturnsTheKeysInThatLineWhenAskedForAContext() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
		}

		@Test
		public void testReturnsTheKeysInThatLineWhenAskedForNoConext() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", null).get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 2,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
		}

	}

	public static class WhenThereAreConfigurationsFormSeveralForms
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

			StringBundler sb1 = new StringBundler(5);

			sb1.append("general");
			sb1.append(StringPool.EQUAL);
			sb1.append("formNavigatorEntryKey1,");
			sb1.append("formNavigatorEntryKey2,");
			sb1.append("formNavigatorEntryKey3");

			StringBundler sb2 = new StringBundler(5);

			sb2.append("general");
			sb2.append(StringPool.EQUAL);
			sb2.append("formNavigatorEntryKey4,");
			sb2.append("formNavigatorEntryKey5,");
			sb2.append("formNavigatorEntryKey6");

			createConfiguration("form1", new String[] {sb1.toString()});
			createConfiguration("form2", new String[] {sb2.toString()});
		}

		@Test
		public void testContainsValuesForForm1() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey3", iterator.next());
		}

		@Test
		public void testContainsValuesForForm2() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form2", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey5", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey6", iterator.next());
		}

		@Test
		public void testDoesContainValuesForEntry2IfItIsDeleted() {
			deleteConfiguration("form2");

			Optional<List<String>> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form2", "general", "update");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

	}

	public static class WhenThereAreSeveralConfigurations
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

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

			createConfiguration("form1", new String[] {sb1.toString()});
			createConfiguration("form1", new String[] {sb2.toString()});
		}

		@Test
		public void testContainsValuesForEntry1() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey2", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey3", iterator.next());
		}

		@Test
		public void testContainsValuesForEntry2() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys(
						"form1", "general", "update").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey5", iterator.next());
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownCategory() {
			Optional<List<String>> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys(
						"form1", "unknownCategory", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownContext() {
			Optional<List<String>> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys(
						"form1", "general", "unknownContext");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

		@Test
		public void testReturnsEmptyOptionalForAnUnknownFormId() {
			Optional<List<String>> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("unknownForm", "general", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

	}

	public static class WhenThereIsNoConfigAtAll
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Test
		public void testReturnsEmptyOptional() {
			Optional<List<String>> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add");

			Assert.assertFalse(formNavigatorEntryKeys.isPresent());
		}

	}

	public static class WhenThereIsOneConfigurationWithTwoLinesForSameContext
		extends BaseFormNavigatorEntryConfigurationRetrieverTestCase {

		@Override
		public void setUp() throws Exception {
			super.setUp();

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

			createConfiguration(
				"form1", new String[] {sb1.toString(), sb2.toString()});
		}

		@Test
		public void testTheLastOneHasPrecedence() {
			List<String> formNavigatorEntryKeys =
				formNavigatorEntryConfigurationRetriever.
					getFormNavigatorEntryKeys("form1", "general", "add").get();

			Assert.assertEquals(
				formNavigatorEntryKeys.toString(), 3,
				formNavigatorEntryKeys.size());

			Iterator<String> iterator = formNavigatorEntryKeys.iterator();

			Assert.assertEquals("formNavigatorEntryKey1", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey4", iterator.next());
			Assert.assertEquals("formNavigatorEntryKey5", iterator.next());
		}

	}

}