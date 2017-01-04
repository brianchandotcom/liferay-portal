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

package com.liferay.frontend.taglib.form.navigator.util;

import com.liferay.portal.kernel.util.StringBundler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Enclosed.class)
public class FormNavigatorEntryVisibilityConfigurationHelperTest {

	public static class WhenThereAreTwoConfigurationsWithOneLine {

		@Test
		public void testContainsValueInFirstConfigurationFirstPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInFirstConfigurationLastPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry3";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInFirstConfigurationMiddlePosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry2";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInSecondConfigurationFirstPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "update.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInSecondConfigurationLastPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "update.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry5";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInSecondConfigurationMiddlePosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(4);

			sb2.append("update.general=");
			sb2.append("com.liferay.FormNavigatorEntry1,");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "update.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry4";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testThatBothConfigurationsAreMergedForSameTarget() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb1 = new StringBundler(4);

			sb1.append("add.general=");
			sb1.append("com.liferay.FormNavigatorEntry1,");
			sb1.append("com.liferay.FormNavigatorEntry2,");
			sb1.append("com.liferay.FormNavigatorEntry3");

			StringBundler sb2 = new StringBundler(3);

			sb2.append("add.general=");
			sb2.append("com.liferay.FormNavigatorEntry4,");
			sb2.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb1.toString(), sb2.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry2";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry3";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry4";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry5";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

	}

	public static class WhenThereIsOneConfigurationWithOneLine {

		@Test
		public void testContainsValueInFirstPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInLastPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry3";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInMiddlePosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry2";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testDoesNotContainIncompleteValues() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName = "com.liferay.FormNavigator";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testDoesNotContainIncorrectPrefix() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.miscellaneous";
			String formNavigatorEntryClassName = "com.liferay.FormNavigator";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testDoesNotContainIncorrectValues() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(4);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName = "com.test.FormNavigator";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

	}

	public static class WhenThereIsOneConfigurationWithTwoLines {

		@Test
		public void testContainsValueInFirstPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("update.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			prefix = "update.general";
			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInLastPosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("update.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry3";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			prefix = "update.general";
			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry5";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testContainsValueInMiddlePosition() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("update.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry2";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			prefix = "update.general";
			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry4";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testDoesNotContainIncorrectPrefix() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("update.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.miscellaneous";
			String formNavigatorEntryClassName = "com.liferay.FormNavigator";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

		@Test
		public void testDoesNotContainIncorrectValues() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("update.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName = "com.test.FormNavigator";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			prefix = "update.general";

			Assert.assertFalse(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

	}

	public static class WhenThereIsOneConfigurationWithTwoLinesForSameTarget {

		@Test
		public void testThatBothLinesAreMerged() {
			FormNavigatorEntryVisibilityConfigurationHelper
				formNavigatorEntryVisibilityConfigurationHelper =
					new FormNavigatorEntryVisibilityConfigurationHelper();

			StringBundler sb = new StringBundler(9);

			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry1,");
			sb.append("com.liferay.FormNavigatorEntry2,");
			sb.append("com.liferay.FormNavigatorEntry3");
			sb.append("\n");
			sb.append("add.general=");
			sb.append("com.liferay.FormNavigatorEntry4,");
			sb.append("com.liferay.FormNavigatorEntry5");

			String[] hiddenFormNavigatorEntryQueries =
				new String[] {sb.toString()};

			String prefix = "add.general";
			String formNavigatorEntryClassName =
				"com.liferay.FormNavigatorEntry1";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry2";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry3";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry4";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));

			formNavigatorEntryClassName = "com.liferay.FormNavigatorEntry5";

			Assert.assertTrue(
				formNavigatorEntryVisibilityConfigurationHelper.
					containsFormNavigatorEntryClassName(
						hiddenFormNavigatorEntryQueries, prefix,
						formNavigatorEntryClassName));
		}

	}

}